import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can create a bibtext file'

scenario "user can create a new bibtext file", {
    given 'file name has been given and command (1) Create new reference is selected', {
        fio = new StubFileIO();
        dao = new FileReferenceDao(fio)
        io = new StubIO("test.bib","1", "1", "Kullervo","A. Kivi", "Kullervo", 
        "tidi", "1900", "6", "2","13--16","7","","", "6")
        ui = new UI(io, dao)
    }

    when 'valid information has been added', {
        ui.run()
    }

    then 'a bibtext file has been created', {
        result = dao.toBibtex()
        result.shouldNotBe null
        result.shouldHave("@article { Kullervo,")
    }
}
