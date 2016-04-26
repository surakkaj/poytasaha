import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*
import java.util.Scanner;

description 'User can add a new article reference to a file in bibtex-format'

scenario "user can add an article reference by giving required information", {
    given 'commands Create new reference and Article are selected', {        
        dao = new FileReferenceDao()
        io = new StubIO("a.txt","1", "1", "abc","O. Olio", "Ketterät menetelmät", 
        "Ohjelmointi", "2012", "6",  "2","13--16","","","", "5")
        ui = new UI(io, dao)
    }

    when 'required article reference information is entered', {
        ui.run()
    }

    then 'a new article reference has been added', {
        article = dao.searchByCitationKey("abc")
        article.shouldNotBe null
        article.shouldHave("author:O. Olio")
    }
}
