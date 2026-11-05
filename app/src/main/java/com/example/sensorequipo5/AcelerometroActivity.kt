package com.example.sensorequipo5

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AcelerometroActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tvValoresX : TextView
    private lateinit var tvValoresY : TextView
    private lateinit var tvValoresZ: TextView
    private lateinit var tvEstado : TextView
    private lateinit var panel : LinearLayout
    private lateinit var tvTitulo : TextView
    private lateinit var btnContador : Button

    private var contadorBrusco = 0
    private var contadorNormal = 0

    private var estadoActual = ""

    //Crear un atributo que permita el manejo de sensores
    private lateinit var sensorManager: SensorManager

    //Atributo que permite usar el sensor
    private var sensorAcelerometro : Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acelerometro)

        tvValoresX = findViewById(R.id.tvValoresX)
        tvValoresY = findViewById(R.id.tvValoresY)
        tvValoresZ = findViewById(R.id.tvValoresZ)
        tvEstado = findViewById(R.id.tvEstado)
        panel = findViewById(R.id.panel)
        tvTitulo = findViewById(R.id.tvTitulo)
        btnContador = findViewById(R.id.btnContador)

        //Indicar que el sensor ocupara los servicios del sistema
        sensorManager = getSystemService(SENSOR_SERVICE)as SensorManager

        //Hacer uso del sensor aceleropmetro
        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        //Verificar si el sensor de luz esta disponible
        if(sensorAcelerometro == null){
            tvEstado.text = "No hay movimiento :("
        }

        btnContador.setOnClickListener {
            Toast.makeText(
                this,
                "Movimientos bruscos: $contadorBrusco\nMovimientos normales: $contadorNormal",
                Toast.LENGTH_SHORT
            ).show()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Metodos obligatorios de la clase
    //Metodo para ver los cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {

        //Validar que el sensor sea el correcto
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){

            //Variable para guardar valores
            val x =  event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            tvValoresX.text = "X: $x"
            tvValoresY.text = "Y: $y"
            tvValoresZ.text = "Z: $z"



            if (Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12){
                if (estadoActual != "BRUSCO") {
                    contadorBrusco++
                    estadoActual = "BRUSCO"
                }
                tvEstado.text = " Movimiento brusco detectado:o"
                panel.setBackgroundColor(Color.RED)
                tvEstado.setTextColor(Color.BLACK)
                tvValoresX.setTextColor(Color.BLACK)
                tvValoresY.setTextColor(Color.BLACK)
                tvValoresZ.setTextColor(Color.BLACK)
                tvTitulo.setTextColor(Color.BLACK)



        }else if (Math.abs(x) < 12 || Math.abs(y) < 12 || Math.abs(z) < 12){
                if (estadoActual != "NORMAL") {
                    contadorNormal++
                    estadoActual = "NORMAL"
                }

                tvEstado.text = "Movimiento normal:)"
                panel.setBackgroundColor(Color.GREEN)
                tvEstado.setTextColor(Color.BLACK)
                tvValoresX.setTextColor(Color.BLACK)
                tvValoresY.setTextColor(Color.BLACK)
                tvValoresZ.setTextColor(Color.BLACK)
                tvTitulo.setTextColor(Color.BLACK)


            }

        }

    }




    //Metodo para hacer cambios en los valores matemáticos
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }

    //METODO PARA APAGAR EL  COMPONENTE (SENSOR)
    override fun onResume() {
        super.onResume()

        sensorAcelerometro?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

}