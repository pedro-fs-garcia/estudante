package com.estudante.models

import android.net.Uri

class StudentCard {
    var primaryLogo: Uri? = null
    var secondaryLogo: Uri? = null
    var profileImage: Uri? = null

    var studentName: String = ""
    var studentId: Long = 0
    var studentCourse: String = ""
    var studentCicle: String = ""
    var year: String = ""

}