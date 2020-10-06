/*
Copyright 2020 Chris Basinger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.evilthreads.drawersnifferlib

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
/*
            (   (                ) (             (     (
            )\ ))\ )    *   ) ( /( )\ )     (    )\ )  )\ )
 (   (   ( (()/(()/(  ` )  /( )\()|()/((    )\  (()/( (()/(
 )\  )\  )\ /(_))(_))  ( )(_)|(_)\ /(_))\((((_)( /(_)) /(_))
((_)((_)((_|_))(_))   (_(_()) _((_|_))((_))\ _ )(_))_ (_))
| __\ \ / /|_ _| |    |_   _|| || | _ \ __(_)_\(_)   \/ __|
| _| \ V /  | || |__    | |  | __ |   / _| / _ \ | |) \__ \
|___| \_/  |___|____|   |_|  |_||_|_|_\___/_/ \_\|___/|___/
....................../´¯/)
....................,/¯../
.................../..../
............./´¯/'...'/´¯¯`·¸
........../'/.../..../......./¨¯\
........('(...´...´.... ¯~/'...')
.........\.................'...../
..........''...\.......... _.·´
............\..............(
..............\.............\...
*/
class DrawerSniffer{
    companion object {
        private val channel = Channel<InterceptedNotification>()

        /*send a notification event*/
        internal suspend fun publish(notif: InterceptedNotification) = channel.send(notif)

        /*subscribe to notification events*/
        /*use this method to receive notifications as they are posted. Write the code in the consume block to handle each notification as it is intercepted*/
        @ExperimentalCoroutinesApi
        suspend fun subscribe(consume: (InterceptedNotification) -> Unit) = channel.consumeEach { notification -> consume(notification) }

        /*checks whether the user has enabled notification listener services for you app in the notification services settings screen*/
        fun hasPermission(ctx: Context): Boolean {
            val cn = ComponentName(ctx, NotificationInterceptorService::class.java)
            val flat = Settings.Secure.getString(ctx.contentResolver, "enabled_notification_listeners")
            return flat != null && flat.contains(cn.flattenToString())
        }

        /*request notification listening services for your package or application*/
        fun requestPermission(ctx: Context) {
            val intent = Intent().apply {
                action = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            ctx.startActivity(intent)
        }
    }
}

/*data type for an intercepted notification and is used in the channel for posting events*/
data class InterceptedNotification(val title: CharSequence, val body: CharSequence, val footer: String, val app: String, val time: Long)