/**
 *
 */
package batch;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import proto.ProtocolBuffersBook;



/**
 * @author Yusuke Kyan
 *
 */
public class ProtocolBuffersTest {
  @Test
  public void testProtocolBuffers() throws IOException {
      final ProtocolBuffersBook.BookData.Builder bookBuilder = ProtocolBuffersBook.BookData.newBuilder();
      bookBuilder.setIsbn("978-4774169316");
      bookBuilder.setTitle("Javaエンジニア養成読本 [現場で役立つ最新知識、満載!] ");
      bookBuilder.setPrice(1980);
      bookBuilder.addAllTags(Arrays.asList("あなたと", "Java", "今すぐ", "ダウンロード!"));

      final ProtocolBuffersBook.BookData src = bookBuilder.build();
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();

      // シリアライズ
      src.writeTo(baos);

      final byte[] binary = baos.toByteArray();

      // シリアライズ後のサイズ
      assertThat(binary.length, is(148));

      // デシリアライズ
      final ProtocolBuffersBook.BookData dest = ProtocolBuffersBook.BookData.parseFrom(new ByteArrayInputStream(binary));

      assertThat(dest.getIsbn(), is("978-4774169316"));
      assertThat(dest.getTitle(), is("Javaエンジニア養成読本 [現場で役立つ最新知識、満載!] "));
      assertThat(dest.getPrice(), is(1980));
      assertThat(dest.getTagsList(), contains("あなたと", "Java", "今すぐ", "ダウンロード!"));
  }
}
