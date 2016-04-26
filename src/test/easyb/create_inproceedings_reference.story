import ohtu.*
import ohtu.UI.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.io.*

description 'User can add a new inproceedings reference to a file in bibtex-format'

scenario "user can add a inproceedings reference by giving required information", {
    given 'commands Create new reference and inproceedings are selected', { 
        dao = new FileReferenceDao()
        io = new StubIO("c.bib","1", "2", "rk3","Risto Runoilija", "Runolliset algoritmit", 
        "Taide ja tietojenkäsittely", "2014","A. Aalto", "2","Tiede ja taide","145--157",
        "Suomi","4","TT","Julkaisijat Oy", "5") 
        ui = new UI(io, dao)
    }

    when 'required inproceedings reference information is entered', {
        ui.run()
    }

    then 'a new inproceedings reference has been added', {
        inpr = dao.searchByCitationKey("rk3")
        inpr.shouldNotBe null
        inpr.shouldHave("author:Risto Runoilija")
    }
}
