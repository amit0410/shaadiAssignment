package com.example.shaadi.feature.contract

import com.squareup.moshi.Json

data class ProfileResponse (
    @Json(name = "results") @field:Json(name = "results") val results: List<UserModel>
)

data class UserModel(
    @Json(name = "gender") @field:Json(name = "gender") val gender: String?,
    @Json(name = "email") @field:Json(name = "email") val email: String?,
    @Json(name = "name") @field:Json(name = "name") val name: Name?,
    @Json(name = "location") @field:Json(name = "location") val location: Location? = null,
    @Json(name = "phone") @field:Json(name = "phone") val phone: String? = null,
    @Json(name = "cell") @field:Json(name = "cell") val cell: String? = null,
    @Json(name = "id") @field:Json(name = "id") val id: ID? = null,
    @Json(name = "login") @field:Json(name = "login") val login: Login? = null,
    @Json(name = "picture") @field:Json(name = "picture") val picture: Picture? = null,
    @Json(name = "dob") @field:Json(name = "dob") val dob: DOB? = null,
    @Json(name = "registered") @field:Json(name = "registered") val registered: DOB? = null,
    @Json(name = "status") @field:Json(name = "status") val status: String = "NotResponded"
)

data class Name(
    @Json(name = "title") @field:Json(name = "title") val title: String?,
    @Json(name = "first") @field:Json(name = "first") val first: String?,
    @Json(name = "last") @field:Json(name = "last") val last: String?
)

data class Location(
        @Json(name = "street") @field:Json(name = "street") val street: Street?,
    @Json(name = "city") @field:Json(name = "city") val city: String = "",
    @Json(name = "state") @field:Json(name = "state") val state: String = "",
    @Json(name = "country") @field:Json(name = "country") val country: String = "",
    @Json(name = "postcode") @field:Json(name = "postcode") val postcode: String = ""
)

data class ID(
    @Json(name = "name") @field:Json(name = "name") val name: String?,
    @Json(name = "value") @field:Json(name = "value") val value: String?
)

data class Street(
        @Json(name = "name") @field:Json(name = "name") val name: String = "",
        @Json(name = "number") @field:Json(name = "number") val number: Int? = 1
)

data class Picture(
    @Json(name = "large") @field:Json(name = "large") val large: String?,
    @Json(name = "medium") @field:Json(name = "medium") val medium: String?,
    @Json(name = "thumbnail") @field:Json(name = "thumbnail") val thumbnail: String?
)

data class DOB(
    @Json(name = "date") @field:Json(name = "date") val date: String? = null,
    @Json(name = "age") @field:Json(name = "age") val age: Int = 20
)

data class Login(
    @Json(name = "uuid") @field:Json(name = "uuid") val uuid: String? = null,
    @Json(name = "username") @field:Json(name = "username") val username: String?,
    @Json(name = "password") @field:Json(name = "password") val password: String? = null,
    @Json(name = "salt") @field:Json(name = "salt") val salt: String? = null,
    @Json(name = "md5") @field:Json(name = "md5") val md5: String? = null,
    @Json(name = "sha1") @field:Json(name = "sha1") val sha1: String? = null,
    @Json(name = "sha256") @field:Json(name = "sha256") val sha256: String? = null
)

