package cz.pslib.unittests

class UserManager(private val repository: UserRepository) {
    fun welcomeMessage(userId: Int): String {
        val userName = repository.getUserName(userId)
        return "Welcome, $userName!"
    }
}