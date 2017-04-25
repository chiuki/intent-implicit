package com.sqisland.intent.implicit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
  private static final int REQUEST_CODE_GALLERY = 1000;

  private ImageView imageView;
  private Uri imageUri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView = (ImageView) findViewById(R.id.image);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
      try {
        imageUri = data.getData();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        imageView.setImageBitmap(bitmap);
      } catch (IOException e) {
      }
      return;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  public void link(View view) {
    Uri uri = Uri.parse("https://www.pluralsight.com");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

  public void mapLink(View view) {
    Uri uri = Uri.parse("http://maps.google.com/maps?q=22.276556,114.160573");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

  public void mapGeo(View view) {
    Uri uri = Uri.parse("geo:22.276556,114.160573");
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

  public void dial(View view) {
    Uri uri = Uri.parse("tel:3035551212");
    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
    startActivity(intent);
  }

  public void gallery(View view) {
    Intent intent = new Intent();
    intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    intent.putExtra("return-data", true);
    startActivityForResult(intent, REQUEST_CODE_GALLERY);
  }

  public void sendText(View view) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_TEXT, "Greetings from the intents course");
    intent.setType("text/plain");
    startActivity(Intent.createChooser(intent, getString(R.string.send_to)));
  }

  public void sendImage(View view) {
    if (imageUri == null) {
      Toast.makeText(this, R.string.no_image_picked_error, Toast.LENGTH_SHORT).show();
      return;
    }
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
    intent.setType(getContentResolver().getType(imageUri));
    startActivity(Intent.createChooser(intent, getString(R.string.send_to)));
  }

  public void sendEmail(View view) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("message/rfc822");
    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "recipient@example.com" });
    intent.putExtra(Intent.EXTRA_SUBJECT, "Implicit intents");
    intent.putExtra(Intent.EXTRA_TEXT, "Writes your email for you");
    startActivity(Intent.createChooser(intent, getString(R.string.send_to)));
  }
}