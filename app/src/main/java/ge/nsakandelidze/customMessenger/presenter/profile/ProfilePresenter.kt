package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.presenter.profile.validator.ProfileValidator
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.profile.IProfile

class ProfilePresenter(val view: IProfile) {
    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val validator: ProfileValidator = ProfileValidator()

    fun updateUserData(nickname: String, profession: String) {
        val validateInput = validator.validateInput(nickname, profession)
        if (!validateInput) {
            view.showMessage("nickname and profession fields cant be empty.")
        } else {
            val userId = userStateStorage.getIdOfUser()
            userDataStorage.getUserDataWithIdOf(userId) {
                userDataStorage.updateUserWithIdOf(userId, nickname, it.password!!, profession, {
                    userStateStorage.signOut()
                    userStateStorage.signIn(nickname)
                    view.updateUserFields(nickname, profession)
                }, {
                    view.showMessage("Couldn't update nickname, it already exists.")
                })
            }
        }
    }

    fun getUserData() {
        val idOfUser = userStateStorage.getIdOfUser()
        userDataStorage.getUserDataWithIdOf(idOfUser) {
            view.updateUserFields(it.nickname.orEmpty(), it.profession.orEmpty())
        }
    }

    fun signOut() {
        userStateStorage.signOut()
        view.redirectToView("bla")
    }
}