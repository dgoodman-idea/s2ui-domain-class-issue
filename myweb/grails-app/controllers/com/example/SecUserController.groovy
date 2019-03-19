package com.example

import grails.validation.ValidationException
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])

class SecUserController {

    SecUserService secUserService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secUserService.list(params), model:[secUserCount: secUserService.count()]
    }

    def show(Long id) {
        respond secUserService.get(id)
    }

    def create() {
        respond new SecUser(), model: [roleList: SecRole.listOrderByAuthority()]
    }

    def save(SecUser secUser) {
        if (secUser == null) {
            notFound()
            return
        }

        try {
            secUserService.save(secUser)
            this.addRoles(secUser)
        } catch (ValidationException e) {
            respond secUser.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUser.id])
                redirect secUser
            }
            '*' { respond secUser, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond secUserService.get(id), model: [roleList: SecRole.listOrderByAuthority()]
    }

    def update(SecUser secUser) {
        if (secUser == null) {
            notFound()
            return
        }

        if (doUpdate(secUser)) {
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'secUser.label', default: 'SecUser'), secUser.id])
                    redirect secUser
                }
                '*' { respond secUser, [status: OK] }
            }
        }
        else {
            respond secUser.errors, view: 'edit', model: [roleList: SecRole.listOrderByAuthority()]
        }
    }


    @Transactional
    private boolean doUpdate(secUser) {
        if (secUser.save(flush: true)) {
            SecUserSecRole.removeAll(secUser)  // remove all roles
            addRoles(secUser)  // add the new roles - which may be the old ones
            return true
        }
        else{
            return false
        }
    }

    private void addRoles(secUser) {
        if(!secUser.isAttached()){
            secUser.attach()
        }
        def roleIds = params.secRole
        if(roleIds instanceof java.lang.String){
            def role = SecRole.get(Integer.parseInt(roleIds))
            if(!role.isAttached()){
                role.attach()
            }
            new SecUserSecRole(secUser: secUser, secRole: role).save(flush: true)
        }
        else{
            roleIds.each{
                def role = SecRole.get(Integer.parseInt(it))
                if(!role.isAttached()){
                    role.attach()
                }
                new SecUserSecRole(secUser: secUser, secRole: role).save(flush: true)
            }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        SecUserSecRole.removeAll(secUser)  // remove all roles

        secUserService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'secUser.label', default: 'SecUser'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUser.label', default: 'SecUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
