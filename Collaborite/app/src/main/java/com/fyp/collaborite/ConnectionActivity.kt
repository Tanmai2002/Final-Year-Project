package com.fyp.collaborite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.text.CaseMap.Title
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.CallSuper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.fyp.collaborite.ui.theme.CollaboriteTheme
import com.fyp.collaborite.components.Components
import com.fyp.collaborite.components.PermissionList
import com.fyp.collaborite.constants.REQUIRED_PERMISSIONS
import com.fyp.collaborite.distributed.wifi.ServiceManager
import com.fyp.collaborite.distributed.wifi.WifiDirectBroadcastReceiver
import com.fyp.collaborite.distributed.wifi.WifiKtsManager
import com.fyp.collaborite.distributed.wifi.WifiManager
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo

class ConnectionActivity : ComponentActivity() {

    private val intentFilter = IntentFilter()
    lateinit var wifiManager:WifiManager
    lateinit var serviceManager: ServiceManager
    lateinit var wifiKtsManager: WifiKtsManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            var roomId by remember { mutableStateOf("") }
            ConnectionPreview()


            }
        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

//        val manager:WifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager


//        wifiManager=WifiManager(this, getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager,manager.initialize(this, mainLooper, null))
//        wifiManager.discoverPeers()
//        serviceManager=ServiceManager(wifiManager)
//        serviceManager.startRegistration()
        wifiKtsManager=WifiKtsManager(this)


    }
    private val REQUEST_CODE_REQUIRED_PERMISSIONS = 1

    @CallSuper
    override fun onStart() {
        super.onStart()
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_REQUIRED_PERMISSIONS
            )
        }
    }
//    @CallSuper
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        val errMsg = "Cannot start without required permissions"
////        if (requestCode == REQUEST_CODE_REQUIRED_PERMISSIONS) {
////            grantResults.forEach {
////                if (it == PackageManager.PERMISSION_DENIED) {
////                    Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show()
////                    finish()
////                    return
////                }
////            }
////            recreate()
////        }
//    }
    public override fun onResume() {
        super.onResume()
//        registerReceiver(
//            wifiManager.registerReceiver(), intentFilter)
    }

    public override fun onPause() {
        super.onPause()
//        unregisterReceiver(wifiManager.receiver)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar(heading: String, modifier: Modifier = Modifier) {
        TopAppBar(title = { Text(text = heading) })

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PeerList(peers: List<String>){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                items(peers){it->

                    val dev: DiscoveredEndpointInfo? =wifiKtsManager.peerMap.get(it);

                    ListItem(
                        modifier = Modifier.fillMaxSize(),
                        headlineText = { Text(dev?.endpointName.orEmpty()) },
                        supportingText = {
                            Text(text =dev?.serviceId.orEmpty()
                            )}, trailingContent = {
                            if(!wifiKtsManager.connectedPeers.contains(it)){
//                                executeTemp(it)


                                Button(onClick = {
//                                    wifiManager.connectPeer(it)
                                    wifiKtsManager.connectPeer(it)

                                }) {
                                    Text("Connect")
                                }
                            }else{

                                Column {
                                    Text("Connected")
                                    Text("${wifiKtsManager.weightInitialized[it]?.count?.value}")
                                    Text("${wifiKtsManager.weightInitialized[it]?.value?.value}")

                                }

                            }
                        })

                }
            })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InputField( modifier: Modifier = Modifier,label:String="",initialValue:String="",onValueChange:(it:Int)->Unit={}) {
        var textField by remember {
            mutableStateOf(initialValue)
        }

        OutlinedTextField(
            modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            value = textField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.isDigitsOnly() && it!=""){
                    textField = it;
                    onValueChange(it.toInt());
                }

                            },
            label = { Text(label) }
        )

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CButton(modifier: Modifier=Modifier,onClick: ()->Unit,text: String="Submit") {
       

        Button(
            onClick = onClick ,content={
            Text(text = text)
        }) 

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun ConnectionPreview() {
        var data by remember {
            mutableStateOf(0)
        }
        var codeNumber by remember {
            mutableStateOf(0)
        }

        CollaboriteTheme {
            Scaffold (
                topBar = { AppBar(heading = "Connection") }
            ){
                Column(modifier=Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Weighted Value:${wifiKtsManager.finalVal.value}")

                    Text(text = "Current Value : ${wifiKtsManager.currentWeight.value.value.value} Count:${wifiKtsManager.currentWeight.value.count.value}")
                    InputField(label = "Start Discovery", onValueChange = {
                        codeNumber=it
                    })
                    Button(
                        enabled = codeNumber!=0,
                        content = { Text(text = "submit")},

                        onClick ={

                        wifiKtsManager.xstartAdvertising(codeNumber.toString())
                        wifiKtsManager.xstartDiscovery()


                    })
                    Text("______")
                    InputField(label = "Send Data", onValueChange = {
                        data=it
                    })
                    Button(

                        content = {Text("Send Data")},
                        enabled = wifiKtsManager.connectedPeers.size>0,
                        onClick = {
                        wifiKtsManager.sendWeights(data)
                    })

                    Text(text = "Peers")
                    PeerList(peers =wifiKtsManager.peers)
                }

            }
        }

    }

}