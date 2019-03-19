package com.example

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])

class SecUserSecRoleController {

    SecUserSecRoleService secUserSecRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secUserSecRoleService.list(params), model:[secUserSecRoleCount: secUserSecRoleService.count()]
    }

    def show(Long id) {
        respond secUserSecRoleService.get(id)
    }

    def create() {
        respond new SecUserSecRole()
    }

    def save(SecUserSecRole secUserSecRole) {
        if (secUserSecRole == null) {
            notFound()
            return
        }

        try {
            secUserSecRoleService.save(secUserSecRole)
        } catch (ValidationException e) {
            respond secUserSecRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'secUserSecRole.label', default: 'SecUserSecRole'), secUserSecRole.id])
                redirect secUserSecRole
            }
            '*' { respond secUserSecRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond secUserSecRoleService.get(id)
    }

    def update(SecUserSecRole secUserSecRole) {
        if (secUserSecRole == null) {
            notFound()
            return
        }

        try {
            secUserSecRoleService.save(secUserSecRole)
        } catch (ValidationException e) {
            respond secUserSecRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'secUserSecRole.label', default: 'SecUserSecRole'), secUserSecRole.id])
                redirect secUserSecRole
            }
            '*'{ respond secUserSecRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        secUserSecRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'secUserSecRole.label', default: 'SecUserSecRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'secUserSecRole.label', default: 'SecUserSecRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
