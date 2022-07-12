import SwiftUI

struct LairView: View {
  
  init() {
    UINavigationBar.appearance().largeTitleTextAttributes =
      [.foregroundColor: UIColor.lairDarkGray]
  }
  
  var profileView: some View {
    LinearGradient.lairHorizontalDark
      .frame(width: 22, height: 22)
      .mask(
        Image(systemName: "person.crop.circle")
          .resizable()
          .scaledToFit()
    )
      .padding()
  }
  
  var body: some View {
    NavigationView {
      ZStack {
        Color.lairBackgroundGray.edgesIgnoringSafeArea(.all)
        VStack(spacing: 20) {
          AccessoryRowView(items: [
            AccessoryView(
              title: "Control Room Lights",
              image: Image(systemName: "lightbulb")),
            AccessoryView(
              title: "Dungeon",
              image: Image(systemName: "lock.shield")),
            AccessoryView(
              title: "Death Ray",
              image: Image(systemName: "scope")),
            AccessoryView(
              title: "Periscope",
              image: Image(systemName: "eye")),
            AccessoryView(
              title: "Evil Music",
              image: Image(systemName: "music.house"))
          ])
          ProgressBarView(
            title: "Death Ray Charge",
            percent: .constant(0.42)).frame(height: 50)
          ProgressBarView(
            title: "Weather Machine Construction",
            percent: .constant(0.86)).frame(height: 50)
        }
        .navigationBarTitle(Text("Lair"))
        .navigationBarItems(trailing: profileView)
      }
    }
  }
}

struct LairView_Previews: PreviewProvider {
  static var previews: some View {
    LairView()
  }
}
