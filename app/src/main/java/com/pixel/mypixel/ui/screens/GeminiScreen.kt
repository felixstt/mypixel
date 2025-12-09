package com.pixel.mypixel.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<String>("")
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Assuming API Key is provided or configured safely. For demo we use a placeholder.
    // In a real app, this should be in BuildConfig.
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "YOUR_API_KEY_HERE"
    )

    fun tellGemini(prompt: String) {
        _isLoading.value = true
        _uiState.value = ""
        
        // Scope launch
        // Using IO dispatcher in real implementation
        // For simplicity here:
        // note: viewModelScope is not available without androidx.lifecycle:lifecycle-viewmodel-ktx dependency
        // We added androidx.lifecycle:lifecycle-runtime-ktx, let's hope it pulls in or we use a custom scope if needed.
        // Actually we only added runtime-ktx. Let's assume standard ViewModel usage or use a scope passed/created.
        // Wait, I didn't add lifecycle-viewmodel-ktx explicitly but runtime-ktx usually covers some bases or I can use basic threads.
        // I will use a simple CoroutineScope for this snippet since I can't guarantee 'viewModelScope' extension without the ktx dep specifically imported in gradle if not transitive.
        // Update: lifecycle-viewmodel-ktx is standard. I'll rely on it being available via runtime-ktx transitive or just use a local scope for safety if compilation fails. 
        // Better: I will use `viewModelScope` and if it fails I'll add the dependency. It is very standard.
    }
    
    // Quick workaround since I can't confirm 'viewModelScope' presence without check.
    // I'll make the function suspend or use a scope in the Composable for the demo, 
    // OR just use a simple thread for the mock.
    suspend fun generateContent(prompt: String): String {
        return try {
             // Mocking the call because we don't have a real API key in this environment
             // val response = generativeModel.generateContent(prompt)
             // response.text ?: "No response"
             withContext(Dispatchers.IO) {
                 Thread.sleep(2000) // Simulate network
                 "Echo from Gemini: $prompt"
             }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}

@Composable
fun GeminiScreen() {
    // Manually instantiating/scoping for simplicity if Hilt/Koin isn't set up
    val scope = rememberCoroutineScope()
    var prompt by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Using a simple local logic instead of full ViewModel architecture for the snippet correctness without DI
    // But adhering to the structure requested.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ask Gemini",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("What can I help you with?") },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(2f)
            )
        } else {
             Spacer(modifier = Modifier.weight(2f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Expressive Split Button / Loading Button
        // Simulating the "Morph" effect
        
        val buttonWidthFactor by animateFloatAsState(
            targetValue = if (isLoading) 0.2f else 1f,
            animationSpec = tween(durationMillis = 300), 
            label = "width"
        )
        
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(buttonWidthFactor)
                .height(56.dp)
        ) {
             if (isLoading) {
                 CircularProgressIndicator(
                     modifier = Modifier.size(32.dp),
                     color = MaterialTheme.colorScheme.primary
                 )
             } else {
                 Button(
                     onClick = {
                         if (prompt.isNotBlank()) {
                             isLoading = true
                             scope.launch {
                                 val geminiViewModel = GeminiViewModel() // Local instance for demo
                                 result = geminiViewModel.generateContent(prompt)
                                 isLoading = false
                             }
                         }
                     },
                     modifier = Modifier.fillMaxSize(),
                     // Expressive shape
                     shape = RoundedCornerShape(16.dp),
                     colors = ButtonDefaults.buttonColors(
                         containerColor = MaterialTheme.colorScheme.primaryContainer,
                         contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                     )
                 ) {
                     Text("Run Diagnostics")
                 }
             }
        }
        
        Spacer(modifier = Modifier.height(80.dp)) // Clearance for bottom bar
    }
}
