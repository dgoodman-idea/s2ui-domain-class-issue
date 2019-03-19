package myplugin

import grails.gorm.transactions.Transactional

@Transactional
class NameService {

    def name() {
      'Dave'
    }
}
