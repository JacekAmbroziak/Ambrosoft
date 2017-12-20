package com.ambrosoft

/* Utility functions */

package object conference {
  def hoursToMinutes(hour: Int): Int = hour * 60

  // uses European hour display (we could alternatively implement AM/PM formatting)
  def minutesToTimeString(minutes: Int): String = f"${minutes / 60}:${minutes % 60}%02d"
}
