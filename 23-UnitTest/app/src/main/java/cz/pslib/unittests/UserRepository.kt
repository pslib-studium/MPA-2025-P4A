package cz.pslib.unittests

interface UserRepository {
    fun getUserName(userId: Int): String
}