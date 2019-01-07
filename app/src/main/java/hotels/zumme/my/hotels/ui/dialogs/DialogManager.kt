package hotels.zumme.my.hotels.ui.dialogs


class DialogManager {

    private var dialog: BaseDialogFragment? = null

    companion object {
        val instance = DialogManager()
    }

    fun isShowing(): Boolean {
        return if (dialog != null) {
            dialog!!.isVisible
        } else {
            false
        }
    }

    fun dismiss() {
        if (dialog != null) {
           dialog!!.dismiss()
        }
    }
}