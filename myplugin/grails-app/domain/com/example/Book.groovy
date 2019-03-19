package com.example

class Book {

    String title, description, year
    
    static hasMany = [authors: Author]
    
    static constraints = {
	title blank: false
	description blank: false
	year matches: /\d{4}/
	authors minSize: 1
    }
    
    String toString(){
	title
    }
}
