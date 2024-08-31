package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    UnitConverter(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                    )
                }
            }
        }
    }

}

@Composable
fun UnitConverter(modifier: Modifier) {
    var inputValue by remember {
        mutableStateOf("")
    }
    var outputValue by remember {
        mutableStateOf("0.0")
    }
    var inputType by remember {
        mutableStateOf("Centimeters")
    }
    var outputType by remember {
        mutableStateOf("Meters")
    }
    var isFirstDropDownExpanded by remember {
        mutableStateOf(false)
    }
    var isSecondDropDownExpanded by remember {
        mutableStateOf(false)
    }
    var conversionFactor by remember {
        mutableDoubleStateOf(0.1)
    }

    // Helper function to get the conversion factor
    fun getConversionFactor(from: String, to: String): Double {
        return when (from to to) {
            "Millimeters" to "Centimeters" -> 0.1
            "Centimeters" to "Millimeters" -> 10.0
            "Centimeters" to "Meters" -> 0.01
            "Meters" to "Centimeters" -> 100.0
            "Meters" to "Feet" -> 3.28084
            "Feet" to "Meters" -> 0.3048
            "Millimeters" to "Meters" -> 0.001
            "Meters" to "Millimeters" -> 1000.0
            "Millimeters" to "Feet" -> 0.00328084
            "Feet" to "Millimeters" -> 304.8
            "Centimeters" to "Feet" -> 0.0328084
            "Feet" to "Centimeters" -> 30.48
            else -> 1.0
        }
    }

    // Function to perform the conversion
    fun performConversion() {
        val input = inputValue.toDoubleOrNull()
        if (input == null) {
            conversionFactor = 1.0
            outputValue = "0.0"
            return
        }
        conversionFactor = getConversionFactor(inputType, outputType)
        outputValue = (input * conversionFactor).toString()
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue,
            singleLine = true,
            onValueChange = { value ->
                inputValue = value
                performConversion() // Update output on input change
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Enter Value") })
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(
                    onClick = {
                        isFirstDropDownExpanded = !isFirstDropDownExpanded
                        performConversion()
                    },
                ) {
                    Text(text = inputType.ifEmpty { "Select" })
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
                DropdownMenu(
                    expanded = isFirstDropDownExpanded,
                    onDismissRequest = {
                        isFirstDropDownExpanded = !isFirstDropDownExpanded
                        performConversion()
                    },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            inputType = "Millimeters"
                            isFirstDropDownExpanded = !isFirstDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            inputType = "Centimeters"
                            isFirstDropDownExpanded = !isFirstDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            inputType = "Meters"
                            isFirstDropDownExpanded = !isFirstDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            inputType = "Feet"
                            isFirstDropDownExpanded = !isFirstDropDownExpanded
                            performConversion()
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(
                    onClick = {
                        isSecondDropDownExpanded = !isSecondDropDownExpanded
                    },
                ) {
                    Text(text = outputType.ifEmpty { "Select" })
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
                DropdownMenu(expanded = isSecondDropDownExpanded, onDismissRequest = {
                    isSecondDropDownExpanded = !isSecondDropDownExpanded
                    performConversion()
                }) {
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = {
                            outputType = "Millimeters"
                            isSecondDropDownExpanded = !isSecondDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = {
                            outputType = "Centimeters"
                            isSecondDropDownExpanded = !isSecondDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meters") },
                        onClick = {
                            outputType = "Meters"
                            isSecondDropDownExpanded = !isSecondDropDownExpanded
                            performConversion()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            outputType = "Feet"
                            isSecondDropDownExpanded = !isSecondDropDownExpanded
                            performConversion()
                        },
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result: $outputValue",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}


@Preview
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            UnitConverter(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}




