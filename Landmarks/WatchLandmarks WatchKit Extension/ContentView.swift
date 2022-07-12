//
//  ContentView.swift
//  WatchLandmarks WatchKit Extension
//
//  Created by mitulvaghamshi on 2021-10-14.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        LandmarkList()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
        .environmentObject(ModelData())
    }
}
