package templates
import kw.views.*
import io.kool.template.html.*
fun layout(content:() -> String) = view { "<html><body>${content()}</body><html>"
   //TODO:both idea IDE, idea compilation and maven compilation
   //hangs if I uncomment this
   /* html{
       head{
        title("Kweb Kool template sample:Quotes list")
        this + importJavascript("jquery-1.7.1.min")
        this + importJavascript("bootstrap-alert")
        this + importJavascript("bootstrap-modal")
        this + importLess("bootstrap")

       }
       body{
            div(klass="container"){
                div (id="header", klass="row"){
                    div (klass="span12")  {
                        div (klass="navbar navbar-fixed-top"){
							div (klass="navbar-inner"){
								div (klass="container"){

									img(klass="brand", src='/public/images/ammi.png')
									span(klass="name brand"){
										+"Kweb sample:quotes/span"
									}

									ul (klass="nav"){
									li (klass="active"){
                                        a (href="#",text="Home/a")

                                        li {a( href="#",text="Bills")}
                                        li {a( href="#",text= Company)}

                                        li {a( href=App.quotes_list_page0(), text= "Quotes")}
									
								}

							}

						}
					}
				}


				div (id="content", klass="row"){
					div (klass="span12"){
						this + content()
					}

				}

				div (id="footer"){

					div (klass="navbar navbar-fixed-bottom"){
						div (klass="navbar-inner"){
							div (klass="container"){
								+ "A wonderful world"
							}
						}
					}

				}
			}
	    }
	}
    }
}.toString()!!   */
}
