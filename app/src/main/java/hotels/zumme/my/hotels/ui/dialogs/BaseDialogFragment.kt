package hotels.zumme.my.hotels.ui.dialogs

import android.content.Intent
import android.support.v4.app.DialogFragment


abstract class BaseDialogFragment : DialogFragment() {

    interface IDialogClickListener {

        fun doNegativeClick()

        fun doPositiveClick(intent: Intent)
    }

    protected var mDialogClickListener: IDialogClickListener? = null
        set(value) { mDialogClickListener = value}

}