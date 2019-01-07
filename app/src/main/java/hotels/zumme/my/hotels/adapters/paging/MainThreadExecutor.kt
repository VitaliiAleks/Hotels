package hotels.zumme.my.hotels.adapters.paging

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class MainThreadExecutor : Executor {

    var mHandler: Handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable?) {
        mHandler.post(command)
    }
}