package com.fyp.collaborite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.CaseMap.Title
import android.net.wifi.p2p.WifiP2pManager
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fyp.collaborite.ui.theme.CollaboriteTheme
import com.fyp.collaborite.components.Components
import com.fyp.collaborite.components.PermissionList
import com.fyp.collaborite.constants.REQUIRED_PERMISSIONS
import com.fyp.collaborite.distributed.wifi.WifiDirectBroadcastReceiver
import com.fyp.collaborite.distributed.wifi.WifiManager

class ConnectionActivity : ComponentActivity() {

    private val intentFilter = IntentFilter()
    lateinit var wifiManager:WifiManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            var roomId by remember { mutableStateOf("") }
            CollaboriteTheme {
                Scaffold (
                topBar = {AppBar("Connect Peers")}
                    ){
                        innerPadding->Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    PermissionList(
                        permissions = REQUIRED_PERMISSIONS,
                        applicationContext = applicationContext,
                        currentActivity = this@ConnectionActivity
                    )
                    TextField(value = roomId, onValueChange ={
                        roomId=it
                    }, label = {Text("Create Room")} )
                }


                }


            }

            }
        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        val manager:WifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager


        wifiManager=WifiManager(this, getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager,manager.initialize(this, mainLooper, null))
        wifiManager.discoverPeers()

    }

    public override fun onResume() {
        super.onResume()
        registerReceiver(
            wifiManager.registerReceiver(), intentFilter)
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(wifiManager.receiver)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(heading: String, modifier: Modifier = Modifier) {
        TopAppBar(title = { Text(text = heading) })

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InputField( modifier: Modifier = Modifier,label:String="",initialValue:String="",) {
        var textField by remember {
            mutableStateOf(initialValue)
        }

        OutlinedTextField(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            value = textField,
            onValueChange = { textField = it },
            label = { Text(label) }
        )

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CButton(modifier: Modifier=Modifier,onClick: ()->Unit,text: String="Submit") {
       

        Button(onClick = onClick ,content={
            Text(text = text)
        }) 

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun ConnectionPreview() {
        CollaboriteTheme {
            Scaffold (
                topBar = { AppBar(heading = "Connection") }
            ){
                Column(modifier=Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
                    InputField(label = "Create a Room")
                    CButton( onClick = {

                    })
                    Text("OR")
                    InputField(label = "Join a Room")
                    CButton(onClick = { /*TODO*/ })
                }

            }
        }

    }

}