import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can see a list of existing references'

scenario "user can add two references by giving required information and see a list of references afterwards", {
    given 'references has been added and command List all existing references in file is selected', {
        fio = new StubFileIO();
        dao = new FileReferenceDao(fio)
        io = new StubIO("test.bib","1", "3", "123","Jaana Java", "Olio-ohjelmointi", 
        "Yliopistopaino", "2012", "","","","","","","", "1", "1", "abc","O. Olio", "Ketterät menetelmät", 
        "Ohjelmointi", "2012","","","","","","","2","5") 
        ui = new UI(io, dao)        
    }

    when 'valid information and right commands are entered', {
        ui.run()
        tulosteita = io.getPrints()
    }

    then 'a reference list can be seen', {        
        tulosteita.shouldHave("Listing content of file")
        tulosteita.shouldHave("author = {Jaana Java}")
        tulosteita.shouldHave("author = {O. Olio}")

    }
}
