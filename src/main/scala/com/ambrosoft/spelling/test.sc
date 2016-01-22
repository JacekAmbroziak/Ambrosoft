import com.ambrosoft.spelling.Spelling

Spelling.words("ab cd e")
Spelling.words("ab cd e" +
  " kwas")

Spelling.edits1("Jacek")
Spelling.train(List("a", "b", "c", "a"))

Spelling.NWORDS.size
Spelling.NWORDS.get("water")
Spelling.NWORDS.get("spelling")
Spelling.NWORDS.get("a")
Spelling.NWORDS.get("")
Spelling.known(Set("winter", "jacek", "mountain"))
Spelling.known_edits2("wnter")
Spelling.known_edits2("winten")
Spelling.correct("wnter")
Spelling.correct("speling")
Spelling.correct("spelling")
Spelling.correct("korrecter")
Spelling.correct("jacek")
Spelling.correct("robert")
Spelling.correct("")
Spelling.correct("xyz")
Spelling.correct("x")
Spelling.correct("xyzxyz")
Spelling.correct("abracadabra")
Spelling.correct("sumer")

