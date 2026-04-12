package com.example.reactiontraining.shared.platform

import platform.Foundation.NSDate

actual fun epochMillis(): Long =
    (NSDate.date.timeIntervalSince1970 * 1000.0).toLong()
