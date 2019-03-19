package myweb

import com.example.*
import grails.util.Environment

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        if(Environment.current == Environment.DEVELOPMENT) {
            createDevUsers()
            createDevData()
        }
    }

    def destroy = {
    }

    def createDevUsers() {
        def adminSecRole = new SecRole(authority: 'ROLE_ADMIN').save(failOnError:true)
        def authorSecRole = new SecRole(authority: 'ROLE_AUTHOR').save(failOnError:true)

        def admin = new SecUser(username: "admin", password: springSecurityService.encodePassword('strongPassword')).save(failOnError:true)
        def author = new SecUser(username: "author", password: springSecurityService.encodePassword('strongPassword')).save(failOnError:true)

        SecUserSecRole.create(admin, adminSecRole)
        SecUserSecRole.create(author, authorSecRole)
    }

    def createDevData() {
        def book1 = new Book(title: 'Snowflakes in hell', description: 'Weather conditions in hell', year: '1994').save(failOnError:true)
        def book2 = new Book(title: 'Honesty in parliament', description: 'This is all about wishful thinking', year: '2005').save(failOnError:true)

        def author1 = new Author(name: 'Jon Donne', country: 'Hingland').save(failOnError:true)
        def author2 = new Author(name: 'Koos van der Merwe', country: 'Azania').save(failOnError:true)

        author1.addToBooks(book1).save(failOnError:true)
        author2.addToBooks(book1).save(failOnError:true)
        author2.addToBooks(book2).save(failOnError:true)
    }

}