package com.fubao.hubao.components

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
open class MyApplication


fun main(args: Array<String>) {
    runApplication<MyApplication>(*args)
}
