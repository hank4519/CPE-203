package comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      ArtistComparator ac= new ArtistComparator();
      assertEquals(-14, ac.compare(songs[0], songs[1]));
      assertEquals(-1, ac.compare(songs[5], songs[7]));
      assertEquals(4, ac.compare(songs[3], songs[4]));
      assertEquals(0, ac.compare(songs[0], songs[0]));
   }

   @Test
   public void testLambdaTitleComparator()
   {
      //Comparator<Song> song = (Song s1, Song s2) -> (s1.getTitle().charAt(0) - s2.getTitle().charAt(0));

      Comparator<Song> song = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());
      //The lambda expression is suggested to be replaced by method reference: Comparator.comparing(Song::getTitle)
      //Comparator<Song> song = Comparator.comparing(Song::getTitle);
      assertEquals(8, song.compare(songs[0], songs[1]));
      assertEquals(-18, song.compare(songs[3], songs[2]));
      assertEquals(0, song.compare(songs[4], songs[4]));
   }

   @Test
   public void testYearExtractorComparator()
   {
      //Is this how we suppose to use key extractor?
      Comparator<Song> song = Comparator.comparing(Song::getYear, Comparator.reverseOrder());
      //Love: 2005, Talk: 2006
      assertEquals(1, song.compare(songs[1], songs[2]));
      //Revenge/love both published in 2005
      assertEquals(0, song.compare(songs[0], songs[1]));
      //Sleeping:2007, baker: 1997
      assertEquals(-1, song.compare(songs[4], songs[5]));
   }


   @Test
   public void testComposedComparator()
   {
      Comparator<Song> cp_year = Comparator.comparing(Song::getYear, Comparator.reverseOrder());
      Comparator<Song> cp_artist = Comparator.comparing(Song::getArtist);
      Comparator<Song> cp_title = Comparator.comparing(Song::getTitle);
      ComposedComparator cp1 = new ComposedComparator(cp_year, cp_artist);
      //"D" is 14 letters before R, while it is same in year
      assertEquals(-14, cp1.compare(songs[0], songs[1]));
      //Compare years first, 2006 is after 2005, so compare(2005, 2006) -> 1
      assertEquals(1, cp1.compare(songs[1], songs[2]));
      //Compare title, 'T' is 8 letter ahead of 'L', both same in year
      ComposedComparator cp2 = new ComposedComparator(cp_year, cp_title);
      assertEquals(8, cp2.compare(songs[0], songs[1]));
      //Compare the fourth and eighth, same artist
      ComposedComparator cp3 = new ComposedComparator(cp_artist, cp_year);
      //1998,1978 -> returns -1 as it is in descending order
      assertEquals(-1, cp2.compare(songs[3], songs[7]));
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> cp = Comparator.comparing(Song::getTitle);
      Comparator<Song> then = Comparator.comparing(Song::getArtist);
      //Compare by the artist, G is one letter away from F, and it is greater than F, so positive 1 is expected
      assertEquals(1, cp.thenComparing(then).compare(songs[3], songs[5]));
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> cpArtist = Comparator.comparing(Song::getArtist);
      Comparator<Song> byTitle = Comparator.comparing(Song::getTitle);
      Comparator<Song> byYear = Comparator.comparing(Song::getYear);
      //Pass in the comparator here for Java List sort() method
//      songList.sort(
//         cpArtist.thenComparing(byTitle).thenComparing(byYear)
//      );
      Collections.sort(songList, cpArtist.thenComparing(byTitle).thenComparing(byYear));
      assertEquals(songList, expectedList);
   }
}
