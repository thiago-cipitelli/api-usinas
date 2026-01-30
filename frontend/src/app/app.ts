import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RankingComponent } from './components/ranking/ranking';
import { HeaderComponent } from './components/header/header';
import { FooterComponent } from './components/footer/footer';
import { StatsCardComponent } from './components/stats-card/stats-card';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RankingComponent, HeaderComponent, FooterComponent, StatsCardComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('API Usinas - Ranking de Potência');
}
