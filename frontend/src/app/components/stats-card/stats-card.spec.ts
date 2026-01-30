import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StatsCardComponent } from './stats-card';

describe('StatsCardComponent', () => {
    let component: StatsCardComponent;
    let fixture: ComponentFixture<StatsCardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [StatsCardComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(StatsCardComponent);
        component = fixture.componentInstance;

        // Set inputs
        component.label = 'Teste Label';
        component.value = '123';
        component.icon = '⚡';

        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should display label and value', () => {
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.stats-label')?.textContent).toContain('Teste Label');
        expect(compiled.querySelector('.stats-value')?.textContent).toContain('123');
    });
});
