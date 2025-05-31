/*
 * Copyright 2023-2025 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.wBasis.overview

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

@Sample(
    name = "数据库示例",
    description = "数据库的创建添加",
    documentation = "",
    tags = ["A-Self_demos"],
)
class DbEntryActivity : AppCompatActivity(R.layout.activity_db_entry) {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize DbHelper
        dbHelper = DbHelper(applicationContext)
        dbHelper.writableDatabase // Ensure database is created
        addListeners()
    }

    private fun addListeners() {
        findViewById<Button>(R.id.button_create).setOnClickListener {
            // You may not need to do anything here as the database is already created in onCreate
            Toast.makeText(applicationContext, "Create success.", Toast.LENGTH_LONG).show()
        }
        findViewById<Button>(R.id.button_add).setOnClickListener {
            DbHelper.addToWhitelist(this, "com.wintmain.catalog.app")
            Toast.makeText(this, "Add success.", Toast.LENGTH_LONG).show()
        }
        findViewById<Button>(R.id.button_remove).setOnClickListener {
            DbHelper.removeFromWhitelist(this, "com.wintmain.catalog.app")
            Toast.makeText(this, "Remove success.", Toast.LENGTH_LONG).show()
        }
        findViewById<Button>(R.id.button_getall).setOnClickListener {
            DbHelper.addToWhitelist(this, "com.wintmain.catalog.app1")
            DbHelper.addToWhitelist(this, "com.wintmain.catalog.app2")
            DbHelper.addToWhitelist(this, "com.wintmain.catalog.app3")
            val listall = DbHelper.getWhitelist(this)
            Toast.makeText(this, "GetAll success: $listall", Toast.LENGTH_LONG).show()
        }
    }
}