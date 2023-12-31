package com.maruf.dogprofile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

private const val TAG = "ProfilePage"
@Composable
fun ProfilePage(){
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .verticalScroll(rememberScrollState())) {
        BoxWithConstraints {
            val constraint = if(minWidth<600.dp){
                portraitConstraint()
            }else{
                landescapeConstraint()
            }
            ConstraintLayout(constraint){
                Image(painter = painterResource(id = R.drawable.dog), contentDescription = "Doggy",
                    modifier = Modifier
                        .size(size = 100.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.Red, shape = CircleShape)
                        .layoutId("profileImage")
                    , )
                Text(
                    text = "Doggy",
                    color = Color.Green,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 26.sp,
                    //modifier = Modifier.background(color = Color.Red),
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.layoutId("nameText"),
                )
                Text(text = "Bangladesh",  modifier = Modifier.layoutId("countryName"))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .layoutId("statRow"),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    ProfileStat(value = "120", title = "Followers")
                    ProfileStat(value = "100", title = "Following")
                    ProfileStat(value = "10", title = "Posts")


                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("actionRow"),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Follow User")
                    }

                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Direct Message")
                    }
                }
            }
        }
    }

}

fun portraitConstraint() : ConstraintSet{
    Log.d(TAG, "portraitConstraint: ")
    return ConstraintSet {
        val profileImage = createRefFor("profileImage")
        val nameText = createRefFor("nameText")
        val countryName = createRefFor("countryName")
        val statRow = createRefFor("statRow")
        val actionRow = createRefFor("actionRow")

        constrain(profileImage){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        constrain(nameText){
            top.linkTo(profileImage.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(countryName){
            top.linkTo(nameText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(statRow){
            top.linkTo(countryName.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(actionRow){
            top.linkTo(statRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    }
}
fun landescapeConstraint() : ConstraintSet{
    return ConstraintSet {
        val profileImage = createRefFor("profileImage")
        val nameText = createRefFor("nameText")
        val countryName = createRefFor("countryName")
        val statRow = createRefFor("statRow")
        val actionRow = createRefFor("actionRow")

        constrain(profileImage){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(nameText){
            top.linkTo(profileImage.bottom)
            start.linkTo(profileImage.start)
            end.linkTo(profileImage.end)
        }
        constrain(countryName){
            top.linkTo(nameText.bottom)
            start.linkTo(nameText.start)
            end.linkTo(nameText.end)
        }
        constrain(statRow){
            top.linkTo(parent.top)
            start.linkTo(profileImage.end)
            end.linkTo(parent.end)
        }
        constrain(actionRow){
            top.linkTo(statRow.bottom)
            start.linkTo(actionRow.end)
            end.linkTo(parent.end)
        }

    }
}

@Composable
fun ProfileStat(value : String, title : String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight(700))
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    ProfilePage()
}

