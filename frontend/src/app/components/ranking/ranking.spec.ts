import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { RankingComponent } from './ranking';

describe('RankingComponent', () => {
  let component: RankingComponent;
  let fixture: ComponentFixture<RankingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RankingComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RankingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have correct medals', () => {
    expect(component.getMedalEmoji(0)).toBe('🥇');
    expect(component.getMedalEmoji(1)).toBe('🥈');
    expect(component.getMedalEmoji(2)).toBe('🥉');
    expect(component.getMedalEmoji(3)).toBe('');
  });
});
