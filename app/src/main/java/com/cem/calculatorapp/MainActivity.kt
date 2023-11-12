package com.cem.calculatorapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.children
import com.cem.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    //other
    private var firstNumber= ""
    private var currentNumber= ""
    private var currentOperator= ""
    private var result= ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NoLimitScreen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        //initViews
        binding.apply {
            //get all buttons
            binding.layoutMain.children.filterIsInstance<Button>().forEach{ button ->
                button.setOnClickListener{
                    //get clicked button text
                    val buttonText= button.text.toString()

                    when{
                        buttonText.matches(Regex("[0-9]"))->{
                            if(currentOperator.isEmpty()){
                                firstNumber += buttonText
                                tvResult.text = firstNumber
                            }
                            else{
                                currentNumber += buttonText
                                tvResult.text = currentNumber
                            }
                        }
                        buttonText.matches(Regex("[+\\-*/]"))->{
                            currentNumber = ""
                            if(tvResult.text.toString().isNotEmpty()){
                                currentOperator = buttonText
                                tvResult.text= "0"
                            }
                        }
                        buttonText == "="->{
                            if(currentNumber.isNotEmpty() && currentOperator.isNotEmpty()){
                                tvFormula.text = "$firstNumber$currentOperator$currentNumber"
                                result = evaluateExpression(firstNumber,currentNumber,currentOperator)
                                firstNumber=result
                                tvResult.text = result
                            }
                        }
                        buttonText == "."->{
                            if(currentOperator.isEmpty()){
                                if(! firstNumber.contains(".")){
                                    if (firstNumber.isEmpty()) firstNumber += "0$buttonText"
                                    else firstNumber += buttonText
                                    binding.tvResult.text = firstNumber
                                }
                            }else{
                                if (currentNumber.contains(".")){
                                    if (currentNumber.isEmpty()) currentNumber += "0$buttonText"
                                    else currentNumber += buttonText
                                    tvResult.text = currentNumber
                                }
                            }
                        }
                        buttonText == "C"->{
                            currentNumber = ""
                            currentOperator = ""
                            firstNumber = ""
                            tvResult.text = "0"
                            tvFormula.text = ""
                        }
                    }
                }
            }
        }
    }

    private fun evaluateExpression(firstNumber:String,secondNumber:String,operator:String):String{
        val num1= firstNumber.toDouble()
        val num2= secondNumber.toDouble()
        return when(operator){
            "+"->(num1+num2).toString()
            "-"->(num1-num2).toString()
            "*"->(num1*num2).toString()
            "/"->(num1/num2).toString()
            else ->""
        }
    }
}