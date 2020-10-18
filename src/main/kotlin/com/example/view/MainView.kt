package com.example.view

import tornadofx.*

class MainView : View() {
    override val root = hbox {
        form {
            //Should show Test but shows [test] because the ResourceBundle cannot be read
            label(messages["test"])
        }
    }
}
