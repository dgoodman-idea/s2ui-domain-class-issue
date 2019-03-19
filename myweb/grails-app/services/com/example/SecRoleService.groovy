package com.example

import grails.gorm.services.Service

@Service(SecRole)
interface SecRoleService {

    SecRole get(Serializable id)

    List<SecRole> list(Map args)

    Long count()

    void delete(Serializable id)

    SecRole save(SecRole secRole)

}