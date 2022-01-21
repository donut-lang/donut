pub struct Buffer {
  text: String,
}

impl Buffer {
  fn new(text: &str) -> Self {
    Self {
      text: text.to_owned(),
    }
  }
}
