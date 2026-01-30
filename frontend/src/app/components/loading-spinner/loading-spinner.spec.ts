import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoadingSpinnerComponent } from './loading-spinner';

describe('LoadingSpinnerComponent', () => {
    let component: LoadingSpinnerComponent;
    let fixture: ComponentFixture<LoadingSpinnerComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [LoadingSpinnerComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(LoadingSpinnerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should display custom message', () => {
        fixture.componentRef.setInput('message', 'Aguarde...');
        fixture.detectChanges();
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.loading-text')?.textContent).toContain('Aguarde...');
    });
});
