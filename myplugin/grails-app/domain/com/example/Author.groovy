package com.example

class Author {

    String name, country
	
    static hasMany = [books: Book]
    static belongsTo = Book
    
    static constraints = {
	name blank: false
	country blank: false
    }

    String toString(){
	name
    }

}
