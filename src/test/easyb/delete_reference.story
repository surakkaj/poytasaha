import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can delete an existing reference'

scenario "user can add a reference by giving required information and delete the reference afterwards", {
    given 'reference has been added and command Choose existing reference to remove is selected', { 
        fio = new StubFileIO();
        dao = new FileReferenceDao(fio)
        io = new StubIO("test.bib","1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","", "4", "123", "5") 
        ui = new UI(io, dao)
    }

    when 'citation key of the reference is entered', {
        ui.run()
    }

    then 'a reference has been deleted', {
        book = dao.searchByCitationKey("123")
        book.shouldBe null
    }
}
