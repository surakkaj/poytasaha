import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*
import java.util.Scanner;

description 'User can add a reference to a file in bibtex-format'

scenario "user can add an article reference by entering required information", {
    given 'commands Create new reference and Article are selected', {        
        dao = new FileReferenceDao()
        io = new StubIO("test.bib","1", "1", "abc","O. Olio", "Ketterät menetelmät", 
        "Ohjelmointi", "2012", "6", "2","13--16","7","kiinnostava","oli", "5")
        ui = new UI(io, dao)
    }

    when 'required and optional article reference information is entered', {
        ui.run()
    }

    then 'a new article reference has been added', {
        article = dao.searchByCitationKey("abc")
        article.shouldNotBe null
        article.shouldHave("author:O. Olio")
    }
}

scenario "user can add a book reference by giving required information", {
    given 'commands Create new reference and book are selected', { 
        dao = new FileReferenceDao()
        io = new StubIO("test.bib","1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","", "5") 
        ui = new UI(io, dao)
    }

    when 'required book reference information is entered', {
        ui.run()
    }

    then 'a new book reference has been added', {
        bookWrong = dao.searchByCitationKey("jjj")
        bookWrong.shouldBe null
        bookRight = dao.searchByCitationKey("123")
        bookRight.shouldNotBe null
        bookRight.shouldHave("author:Jaana Java")
    }
}

scenario "user can add a inproceedings reference by giving required information", {
    given 'commands Create new reference and inproceedings are selected', { 
        dao = new FileReferenceDao()
        io = new StubIO("test.bib","1", "2", "rk3","Risto Runoilija", "Runolliset algoritmit", 
        "Taide ja tietojenkäsittely", "2014","A. Aalto", "2","Tiede ja taide","145--157",
        "Suomi","4","TT","Julkaisijat Oy","lue","run", "5") 
        ui = new UI(io, dao)
    }

    when 'required and optional inproceedings reference information is entered', {
        ui.run()
    }

    then 'a new inproceedings reference has been added', {
        inpr = dao.searchByCitationKey("rk3")
        inpr.shouldNotBe null
        inpr.shouldHave("author:Risto Runoilija")
    }
}

scenario "user can add several references by giving required information", {
    given 'commands Create new reference and reference type are selected', {        
        dao = new FileReferenceDao()
        io = new StubIO("test.bib","1", "1", "abc","O. Olio", "Ketterät menetelmät", 
        "Ohjelmointi", "2012", "", "","","","","", "1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","","5")
        ui = new UI(io, dao)
    }

    when 'required article reference information is entered', {
        ui.run()
    }

    then 'more than one references have been added', {
        article = dao.searchByCitationKey("abc")
        article.shouldNotBe null
        book = dao.searchByCitationKey("123")
        book.shouldNotBe null
    }
}
