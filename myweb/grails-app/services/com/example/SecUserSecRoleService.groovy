package com.example

import grails.gorm.services.Service

@Service(SecUserSecRole)
interface SecUserSecRoleService {

    SecUserSecRole get(Serializable id)

    List<SecUserSecRole> list(Map args)

    Long count()

    void delete(Serializable id)

    SecUserSecRole save(SecUserSecRole secUserSecRole)

}