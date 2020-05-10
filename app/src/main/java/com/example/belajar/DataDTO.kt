package com.example.belajar

class HomeModel(var page: Int, var per_page: Int, var total: Int, var total_pages: Int, var data: List<DataObject>, var ad: AdObject)

class DataObject(var id: Int, var email: String, var first_name: String, var last_name: String, var avatar: String)

class AdObject(var company: String, var url: String, var text: String)