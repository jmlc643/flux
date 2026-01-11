package com.jluyo.apps.flux.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(val text: String, val isUser: Boolean)

@HiltViewModel
class AssistantViewModel @Inject constructor() : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage("Hola, soy tu asistente financiero. ¿En qué puedo ayudarte?", false)
    ))
    val messages = _messages.asStateFlow()

    fun sendMessage(text: String) {
        val currentList = _messages.value.toMutableList()
        currentList.add(ChatMessage(text, true))
        _messages.value = currentList

        viewModelScope.launch {
            // Placeholder for LLM API call
            // val response = remoteRepo.askLLM(text, apiKey)
            val response = "Esta es una respuesta simulada. Aquí integrarías tu LLM (OpenAI, Gemini, etc.)."

            val newList = _messages.value.toMutableList()
            newList.add(ChatMessage(response, false))
            _messages.value = newList
        }
    }
}
