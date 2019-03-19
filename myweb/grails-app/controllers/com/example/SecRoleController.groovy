package com.example

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])

class SecRoleController {

    SecRoleService secRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secRoleService.list(params), model:[secRoleCount: secRoleService.count()]
    }

    def show(Long id) {
        respond secRoleService.get(id)
    }

    def create() {
        respond new SecRole()
    }

    def save(SecRole secRole) {
        if (secRole == null) {
            notFound()
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'secRole.label', default: 'SecRole'), secRole.id])
                redirect secRole
            }
            '*' { respond secRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond secRoleService.get(id)
    }

    def update(SecRole secRole) {
        if (secRole == null) {
            notFound()
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'secRole.label', default: 'SecRole'), secRole.id])
                redirect secRole
            }
            '*'{ respond secRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        secRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'secRole.label', default: 'SecRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'secRole.label', default: 'SecRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
