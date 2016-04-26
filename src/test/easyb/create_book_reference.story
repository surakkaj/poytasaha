import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can add a new book reference to a file in bibtex-format'

scenario "user can add a book reference by giving required information", {
    given 'commands Create new reference and book are selected', { 
        dao = new FileReferenceDao()
        io = new StubIO("b.bib","1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","", "5") 
        ui = new UI(io, dao)
    }

    when 'required book reference information is entered', {
        ui.run()
    }

    then 'a new book reference has been added', {
        bookWrong = dao.searchByCitationKey("abc")
        bookWrong.shouldBe null
        bookRight = dao.searchByCitationKey("123")
        bookRight.shouldNotBe null
    }
}
