import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can modify existing reference'

scenario "user can add a reference by giving required information and modify the information afterwards", {
    given 'reference has been added and command Choose existing reference to modify is selected', {
        fio = new StubFileIO();
        dao = new FileReferenceDao(fio)
        io = new StubIO("test.bib","1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","", "3", "123", "Jaana Java", "Scala", 
        "Yliopistopaino", "2012", "","","","","","","", "6") 
        ui = new UI(io, dao)
    }

    when 'citation key of the reference and valid information is entered', {
        ui.run()
    }

    then 'a reference has been modified', {
        book = dao.searchByCitationKey("123")
        book.shouldNotBe null
        book.shouldHave("title:Scala")
    }
}
