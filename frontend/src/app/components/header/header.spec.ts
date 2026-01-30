import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header';

describe('HeaderComponent', () => {
    let component: HeaderComponent;
    let fixture: ComponentFixture<HeaderComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [HeaderComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(HeaderComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have correct title', () => {
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.logo-text')?.textContent).toContain('API Usinas');
    });

    it('should have github link', () => {
        const compiled = fixture.nativeElement as HTMLElement;
        const link = compiled.querySelector('a[href*="github.com"]');
        expect(link).toBeTruthy();
    });
});
