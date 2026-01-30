import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RankingComponent } from './components/ranking/ranking';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RankingComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
