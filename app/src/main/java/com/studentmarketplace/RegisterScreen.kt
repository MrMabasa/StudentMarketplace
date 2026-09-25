package com.studentmarketplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    onRegisterSuccess: (studentNumber: String) -> Unit,
    onBackClick: () -> Unit
) {
    var studentNumber by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = studentNumber,
            onValueChange = {
                studentNumber = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Student Number")
            },
            placeholder = {
                Text("e.g. 12345678")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            },
            placeholder = {
                Text("student@example.com")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirm Password")
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        Button(
            onClick = {
                when {
                    studentNumber.isBlank() -> {
                        errorMessage = "Please enter your student number."
                    }

                    email.isBlank() -> {
                        errorMessage = "Please enter your email address."
                    }

                    password.isBlank() -> {
                        errorMessage = "Please enter a password."
                    }

                    confirmPassword.isBlank() -> {
                        errorMessage = "Please confirm your password."
                    }

                    password != confirmPassword -> {
                        errorMessage = "Passwords do not match."
                    }

                    else -> {
                        isLoading = true
                        errorMessage = ""

                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(
                                email.trim(),
                                password
                            )
                            .addOnCompleteListener { task ->
                                isLoading = false

                                if (task.isSuccessful) {
                                    onRegisterSuccess(studentNumber.trim())
                                } else {
                                    errorMessage =
                                        task.exception?.message
                                            ?: "Registration failed. Please try again."
                                }
                            }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLoading) {
                    "Creating Account..."
                } else {
                    "Register"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedButton(
            onClick = onBackClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}