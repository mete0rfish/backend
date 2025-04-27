package com.onetool.server.api.blueprint.business;

import com.onetool.server.api.blueprint.Blueprint;
import com.onetool.server.api.blueprint.dto.request.BlueprintRequest;
import com.onetool.server.api.blueprint.dto.request.BlueprintUpdateRequest;
import com.onetool.server.api.blueprint.dto.success.BlueprintDeleteSuccess;
import com.onetool.server.api.blueprint.dto.success.BlueprintUpdateSuccess;
import com.onetool.server.api.blueprint.repository.BlueprintRepository;
import com.onetool.server.api.blueprint.service.BlueprintService;
import com.onetool.server.api.fixture.BlueprintFixture;
import com.onetool.server.global.new_exception.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
public class BlueprintBusinessLayerTest {

    private BlueprintBusiness blueprintBusiness;
    private BlueprintService blueprintService;

    @Mock
    private BlueprintRepository blueprintRepository;

    @BeforeEach
    void setUp() {
        blueprintService = new BlueprintService(blueprintRepository);
        blueprintBusiness = new BlueprintBusiness(blueprintService);
    }

    @Test
    void 도면을_성공적으로_생성한다() {
        // given
        final Blueprint blueprint = BlueprintFixture.createBlueprint();
        final BlueprintRequest request = BlueprintFixture.createBlueprintRequest();
        when(blueprintRepository.save(any(Blueprint.class)))
                .thenReturn(blueprint);

        // when
        Blueprint createdBlueprint = blueprintBusiness.createBlueprint(request);

        // then
        assertThat(createdBlueprint).isEqualTo(blueprint);
    }

    @Test
    void 도면을_성공적으로_수정한다() {
        // given
        final BlueprintUpdateRequest request = BlueprintFixture.createBlueprintUpdateRequest();
        final Blueprint blueprint = BlueprintFixture.createBlueprint();
        when(blueprintRepository.findById(eq(request.id())))
                .thenReturn(Optional.ofNullable(blueprint));

        // when
        BlueprintUpdateSuccess success = blueprintBusiness.editBlueprint(request);

        // then
        assertThat(success.isSuccess()).isEqualTo(true);
        assertThat(success.blueprintId()).isEqualTo(request.id());
    }

    @Test
    void 잘못된_ID의_도면수정시_에러를_반환한다() {
        // given
        final BlueprintUpdateRequest request = BlueprintFixture.createWrongIdBlueprintUpdateRequest();
        when(blueprintRepository.findById(eq(request.id())))
                .thenReturn(Optional.empty());

        // when

        // then
        assertThatThrownBy(()->blueprintBusiness.editBlueprint(request))
                .isInstanceOf(ApiException.class)
                .hasMessage("blueprintId : 1000");
    }

    @Test
    void 도면을_성공적으로_삭제한다() {
        // given
        final long blueprintId = 1L;
        when(blueprintRepository.findById(eq(blueprintId)))
                .thenReturn(Optional.of(BlueprintFixture.createBlueprint()));
        doNothing().when(blueprintRepository).delete(any(Blueprint.class));

        // when
        BlueprintDeleteSuccess success = blueprintBusiness.removeBlueprint(blueprintId);

        // then
        assertThat(success.isSuccess()).isEqualTo(true);
    }

    @Test
    void 잘못된_ID의_도면삭제시_에러를_반환한다() {
        // given
        final long blueprintId = 1000L;
        when(blueprintRepository.findById(eq(blueprintId)))
                .thenReturn(Optional.empty());

        // when

        // then
        assertThatThrownBy(()->blueprintBusiness.removeBlueprint(blueprintId))
                .isInstanceOf(ApiException.class)
                .hasMessage("blueprintId : 1000");
    }
}
