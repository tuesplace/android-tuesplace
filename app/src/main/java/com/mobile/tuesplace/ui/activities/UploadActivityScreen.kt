package com.mobile.tuesplace.ui.activities

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.mobile.tuesplace.MULTIPART_NAME_XLSX
import com.mobile.tuesplace.R
import com.mobile.tuesplace.XLSX_FILE_TYPE
import com.mobile.tuesplace.ui.ResultLauncher
import com.mobile.tuesplace.ui.states.EditSpecificationUiState
import com.mobile.tuesplace.ui.states.GetSpecificationUiState
import okhttp3.MultipartBody

@Composable
fun UploadActivityScreen(
    specificationUiState: GetSpecificationUiState,
    onUploadClick: (MultipartBody.Part) -> Unit,
    onGetSpecificationSuccess: (String) -> Unit,
    editSpecificationUiState: EditSpecificationUiState,
    onEditSuccess: () -> Unit,
) {
    when (specificationUiState) {
        GetSpecificationUiState.Empty -> {}
        is GetSpecificationUiState.Error -> {
            val exception = specificationUiState.error
        }
        GetSpecificationUiState.Loading -> {}
        is GetSpecificationUiState.Success -> {
            onGetSpecificationSuccess(specificationUiState.specification[0]._id)
            UploadActivityUi(onUploadClick)
        }
    }

    when (editSpecificationUiState) {
        EditSpecificationUiState.Empty -> {}
        is EditSpecificationUiState.Error -> {
            val error = editSpecificationUiState.error
        }
        EditSpecificationUiState.Loading -> {}
        EditSpecificationUiState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Upload Successful",
                Toast.LENGTH_LONG
            ).show()
            onEditSuccess()
        }
    }
}

@Composable
fun UploadActivityUi(onUploadClick: (MultipartBody.Part) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.dark_blue)),
        contentAlignment = Alignment.Center
    ) {

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Select a file to upload")
            ResultLauncher(XLSX_FILE_TYPE, onUploadClick, modifier = null, MULTIPART_NAME_XLSX)
        }
    }
}